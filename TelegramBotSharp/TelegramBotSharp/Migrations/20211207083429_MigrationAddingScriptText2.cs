using Microsoft.EntityFrameworkCore.Migrations;

namespace TelegramBotSharp.Migrations
{
    public partial class MigrationAddingScriptText2 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "ScriptName",
                table: "CommandEntities",
                newName: "ScriptText");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "ScriptText",
                table: "CommandEntities",
                newName: "ScriptName");
        }
    }
}
