package Bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class TelegramKeyboard {
    private InlineKeyboardButton CreateButton(String[] data){
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(data[0]);
        button.setCallbackData(data[1]);
        return button;
    }

    public InlineKeyboardMarkup CreateMultiLineKeyboard(String[] ...buttons){

        List<InlineKeyboardButton> AllButtons = CreateButtons(buttons);

        List<List<InlineKeyboardButton>> Columns = new ArrayList<>();

        for(int i = 0; i < AllButtons.size(); i+=2){
            List<InlineKeyboardButton> Rows = new ArrayList<>();
            Rows.add(AllButtons.get(i));

            if(i != buttons.length - 1) Rows.add(AllButtons.get(i));

            Columns.add(Rows);
        }

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        markup.setKeyboard(Columns);
        return markup;
    }

    public InlineKeyboardMarkup CreateSingleLineKeyboard(String[] ...buttons){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> Columns = new ArrayList<>();
        Columns.add(CreateButtons(buttons));
        markup.setKeyboard(Columns);

        return markup;
    }

    private List<InlineKeyboardButton> CreateButtons(String[] ...buttons){
        List<InlineKeyboardButton> Rows = new ArrayList<>();

        for(String[] i : buttons){
            if (i.length == 2){
                Rows.add(CreateButton(i));
            }
        }
        return Rows;
    }
}