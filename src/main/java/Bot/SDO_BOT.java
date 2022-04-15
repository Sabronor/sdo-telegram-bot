package Bot;

import SQL.Users;
import Parser.parserSDO;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SDO_BOT extends TelegramLongPollingBot {
    private final String TOKEN;
    private final String NAME;

    Users users = new Users();

    public SDO_BOT(String token, String name){
        super();
        this.TOKEN = token;
        this.NAME = name;
    }
    @Override
    public String getBotUsername() {
        return NAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage answer = new SendMessage();

        if(update.hasMessage()){

            Message message = update.getMessage();
            String name = message.getFrom().getUserName();

            answer.setChatId(message.getChatId().toString());
            if (users.select(String.format("select name from users where name = \"%s\"", name)).size() == 0){
                users.insert_name(name);
            }


            if(message.hasEntities()){
                String command = message.getEntities().get(0).getText();
                
                switch (command){
                    case "/start" -> Start(answer);
                    case "/reg" -> UpdateUserData(message, answer);
                    case "/display" -> DisplayAll(name, answer);
                    case "/timetable" -> TimeTable(name, answer);
                    case "/CreateTable" -> users.CreateTable();
                }
            }

        }else if(update.hasCallbackQuery()){
            CallbackQuery CallBack = update.getCallbackQuery();
            answer.setChatId(CallBack.getMessage().getChatId().toString());
            if ("help".equals(CallBack.getData())) {
                Help(CallBack);
            }
        }

    }

    private void Start(SendMessage answer) {
        answer.setText("Этот бот выводит ваши баллы и расписание прямиком из сдо\nДля начала вам нужно установить логин и пароль");
        answer.setReplyMarkup(new TelegramKeyboard().CreateSingleLineKeyboard(new String[]{"Подсказка", "help"}));
        print(answer);
    }

    private void UpdateUserData(Message message, SendMessage answer) {
        String command = message.getEntities().get(0).getText();
        String name = message.getFrom().getUserName();
        String[] data = message.getText().substring(command.length() + 1).split(" ");
        try {
            users.reg(name, data[0], data[1]);
        }catch (ArrayIndexOutOfBoundsException e){
            answer.setText("Неверный формат ввода данных.\nПопробуйте еще раз.");
            print(answer);
        }
    }

    private void DisplayAll(String name, SendMessage answer){
        String[] data = users.user_data(name);
        for(String i : new parserSDO().Lessons(data[0], data[1])){
            answer.setText(i);
            print(answer);
        }
    }

    private void TimeTable(String name, SendMessage answer) {
        String[] data = users.user_data(name);
        for (String i : new parserSDO().TimeTable(data[0], data[1])){
            answer.setText(i);
            print(answer);
        }
    }

    private void Help(CallbackQuery CallBack){
        EditMessageText editMessageText = EditMessage("Для ввода логина и пароля от сдо:\n/reg логин пароль", CallBack.getMessage().getMessageId(), CallBack.getMessage().getChatId().toString());
        editMessageText.setReplyMarkup(new TelegramKeyboard().CreateSingleLineKeyboard(new String[]{"Подсказка", "help"}));
        print(editMessageText);
    }

    private EditMessageText EditMessage(String text, int MessageId, String ChatId){
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText(text);
        editMessageText.setMessageId(MessageId);
        editMessageText.setChatId(ChatId);
        return editMessageText;
    }

    private void print(SendMessage answer){
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void print(EditMessageText editMessageText){
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}


