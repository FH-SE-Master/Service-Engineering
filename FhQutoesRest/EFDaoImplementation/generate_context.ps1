# Add a reference to EntityFramework Tooling to project file first
#  <ItemGroup>
#    <DotNetCliToolReference Include="Microsoft.EntityFrameworkCore.Tools.DotNet" Version="1.0.1" />
#  </ItemGroup>

dotnet ef dbcontext scaffold "Data Source=(LocalDB)\MSSQLLocalDB;AttachDbFilename=D:\Db\FhQuotesDb.mdf;Integrated Security=True;Connect Timeout=30" "Microsoft.EntityFrameworkCore.SqlServer" -c FhQuotesContext -d -f