namespace Tutorial9.Services;

public interface IDbService
{
    Task DoSomethingAsync();
    Task ProcedureAsync();
    IConfiguration GetConfiguration();
}