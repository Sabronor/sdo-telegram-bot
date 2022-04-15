# sdo-telegram-bot

Бот был создан чисто практики ради.
Выводит расписание и оценки из среды дистанционного обучения РГСУ.

В main:
```Java
       try {
            Properties data = new Properties();
            data.load(new FileInputStream("src/main/resources/config.properties"));

            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new SDO_BOT(data.getProperty("BOT_TOKEN"), data.getProperty("BOT_NAME")));
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
        }
```

В файле config указываются все параметы бота и базы данных
