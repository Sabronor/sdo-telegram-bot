# sdo-telegram-bot

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
