﻿// <auto-generated />
using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;
using TelegramBotSharp.Repository;

namespace TelegramBotSharp.Migrations
{
    [DbContext(typeof(BotDatabaseContext))]
    [Migration("20211207083429_MigrationAddingScriptText2")]
    partial class MigrationAddingScriptText2
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("Relational:MaxIdentifierLength", 63)
                .HasAnnotation("ProductVersion", "5.0.10")
                .HasAnnotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn);

            modelBuilder.Entity("TelegramBotSharp.Repository.Entity.CommandEntity", b =>
                {
                    b.Property<int?>("CommandId")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("integer")
                        .HasAnnotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn);

                    b.Property<string>("CommandName")
                        .HasColumnType("text");

                    b.Property<bool>("IsScript")
                        .HasColumnType("boolean");

                    b.Property<string>("ScriptText")
                        .HasColumnType("text");

                    b.Property<string>("SourceNames")
                        .HasColumnType("text");

                    b.Property<string>("commandAnswer")
                        .HasColumnType("text");

                    b.HasKey("CommandId");

                    b.ToTable("CommandEntities");
                });
#pragma warning restore 612, 618
        }
    }
}
