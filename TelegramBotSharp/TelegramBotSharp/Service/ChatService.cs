using System;
using System.Collections.Generic;
using TelegramBot.Model;
using TelegramBotSharp.TelegramBot;
using TelegramBotSharp.Repository;
using TelegramBotSharp.Repository.Entity;
using NLua;

namespace TelegramBotSharp.Service
{
    public class ChatService : IChatService
    {

        private IBot _bot = null;
        private readonly ICommandsRepository _commandRepoistory;

        public ChatService(ICommandsRepository commandRepository)
        {
            this._commandRepoistory = commandRepository;
        }

        public void processMessage(ChatMessage chatMessage)
        {
            if (_bot != null)
            {
                List<CommandEntity> commands = _commandRepoistory.FindByTrigger(chatMessage.Message);
                if (commands.Count == 0)
                {
                    _bot.SendMessageToChat(new ChatMessage(chatMessage.Source, chatMessage.ChatId, "Неизвестная команда"));
                }
                else
                {
                    if (commands[0].ScriptText != null && commands[0].ScriptText != "")
                    {
                        using (Lua lua = new Lua())
                        {
                            lua.DoString(commands[0].ScriptText);
                            LuaFunction mainFunc = lua["main"] as LuaFunction;
                            string answer = (string)mainFunc.Call()[0];
                            _bot.SendMessageToChat(new ChatMessage(chatMessage.Source, chatMessage.ChatId, answer));
                        }
                    }
                    else
                    {
                        _bot.SendMessageToChat(new ChatMessage(chatMessage.Source, chatMessage.ChatId, commands[0].commandAnswer));
                    }
                }
            }
        }

        public void registerBot(IBot bot)
        {
            _bot = bot;
        }
    }
}
