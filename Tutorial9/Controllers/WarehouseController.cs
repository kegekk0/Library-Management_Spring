using System.Data;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Data.SqlClient;
using Tutorial9.DTOs;
using Tutorial9.Services;

namespace Tutorial9.Controllers;

[Route("api/[controller]")]
[ApiController]
public class WarehouseController : ControllerBase
{
    private readonly IDbService _dbService;
    
    public WarehouseController(IDbService dbService)
    {
        _dbService = dbService;
    }
    
    [HttpPost]
    public async Task<IActionResult> AddProductToWarehouse(WarehouseRequestDto request)
    {
        try
        {
            await using var connection = new SqlConnection(_dbService.GetConfiguration().GetConnectionString("Default"));
            await connection.OpenAsync();
            
            var productCheckCmd = new SqlCommand("SELECT COUNT(1) FROM Product WHERE IdProduct = @IdProduct", connection);
            productCheckCmd.Parameters.AddWithValue("@IdProduct", request.IdProduct);
            if ((int)await productCheckCmd.ExecuteScalarAsync() == 0)
            {
                return NotFound("Product not found");
            }
            
            var warehouseCheckCmd = new SqlCommand("SELECT COUNT(1) FROM Warehouse WHERE IdWarehouse = @IdWarehouse", connection);
            warehouseCheckCmd.Parameters.AddWithValue("@IdWarehouse", request.IdWarehouse);
            if ((int)await warehouseCheckCmd.ExecuteScalarAsync() == 0)
            {
                return NotFound("Warehouse not found");
            }
            
            var orderCheckCmd = new SqlCommand(
                @"SELECT TOP 1 o.IdOrder FROM ""Order"" o 
                LEFT JOIN Product_Warehouse pw ON o.IdOrder = pw.IdOrder 
                WHERE o.IdProduct = @IdProduct AND o.Amount = @Amount 
                AND pw.IdProductWarehouse IS NULL 
                AND o.CreatedAt < @CreatedAt", 
                connection);
            orderCheckCmd.Parameters.AddWithValue("@IdProduct", request.IdProduct);
            orderCheckCmd.Parameters.AddWithValue("@Amount", request.Amount);
            orderCheckCmd.Parameters.AddWithValue("@CreatedAt", request.CreatedAt);
            
            var orderId = await orderCheckCmd.ExecuteScalarAsync();
            if (orderId == null)
            {
                return BadRequest("No matching order found or order already fulfilled");
            }
            
            var priceCmd = new SqlCommand("SELECT Price FROM Product WHERE IdProduct = @IdProduct", connection);
            priceCmd.Parameters.AddWithValue("@IdProduct", request.IdProduct);
            var price = (decimal)await priceCmd.ExecuteScalarAsync();
            
            using var transaction = await connection.BeginTransactionAsync();
            try
            {
                var updateOrderCmd = new SqlCommand(
                    @"UPDATE ""Order"" SET FulfilledAt = @FulfilledAt 
                    WHERE IdOrder = @IdOrder", 
                    connection, (SqlTransaction)transaction);
                updateOrderCmd.Parameters.AddWithValue("@FulfilledAt", DateTime.Now);
                updateOrderCmd.Parameters.AddWithValue("@IdOrder", orderId);
                await updateOrderCmd.ExecuteNonQueryAsync();
                
                var insertCmd = new SqlCommand(
                    @"INSERT INTO Product_Warehouse (IdWarehouse, IdProduct, IdOrder, Amount, Price, CreatedAt)
                    VALUES (@IdWarehouse, @IdProduct, @IdOrder, @Amount, @Price, @CreatedAt);
                    SELECT SCOPE_IDENTITY();", 
                    connection, (SqlTransaction)transaction);
                insertCmd.Parameters.AddWithValue("@IdWarehouse", request.IdWarehouse);
                insertCmd.Parameters.AddWithValue("@IdProduct", request.IdProduct);
                insertCmd.Parameters.AddWithValue("@IdOrder", orderId);
                insertCmd.Parameters.AddWithValue("@Amount", request.Amount);
                insertCmd.Parameters.AddWithValue("@Price", price * request.Amount);
                insertCmd.Parameters.AddWithValue("@CreatedAt", DateTime.Now);
                
                var newId = await insertCmd.ExecuteScalarAsync();
                
                await transaction.CommitAsync();
                
                return Ok(new { IdProductWarehouse = newId });
            }
            catch
            {
                await transaction.RollbackAsync();
                throw;
            }
        }
        catch (Exception ex)
        {
            return StatusCode(500, $"Internal server error: {ex.Message}");
        }
    }
    
    [HttpPost("procedure")]
    public async Task<IActionResult> AddProductToWarehouseWithProcedure(WarehouseRequestDto request)
    {
        try
        {
            await using var connection = new SqlConnection(_dbService.GetConfiguration().GetConnectionString("Default"));
            await using var command = new SqlCommand("AddProductToWarehouse", connection);
            command.CommandType = CommandType.StoredProcedure;
            
            command.Parameters.AddWithValue("@IdProduct", request.IdProduct);
            command.Parameters.AddWithValue("@IdWarehouse", request.IdWarehouse);
            command.Parameters.AddWithValue("@Amount", request.Amount);
            command.Parameters.AddWithValue("@CreatedAt", request.CreatedAt);
            
            await connection.OpenAsync();
            
            var result = await command.ExecuteScalarAsync();
            
            if (result == null)
            {
                return BadRequest("Failed to add product to warehouse");
            }
            
            return Ok(new { IdProductWarehouse = result });
        }
        catch (SqlException ex) when (ex.Number == 50000)
        {
            return BadRequest(ex.Message);
        }
        catch (Exception ex)
        {
            return StatusCode(500, $"Internal server error: {ex.Message}");
        }
    }
}