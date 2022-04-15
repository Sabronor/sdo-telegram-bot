package Parser;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class parserSDO {

    public List<String> Lessons(String name, String pass){
        List<String> ret_list = new ArrayList<>();

        List<HtmlElement> lessonElement = getPage("https://sdo.rgsu.net/subject/list?page_id=m0602&page_id=m0602", name, pass).getByXPath("//div[@id=\"lesson_title\"] | //div[@class=\"score_red number_number\"]");


        for (int i = 0; i < lessonElement.size(); i+=2){
            String lesson_n = lessonElement.get(i).asNormalizedText();
            try {
                int lesson_b = Integer.parseInt(lessonElement.get(i + 1).asNormalizedText());
                ret_list.add(String.format("Предмет: %s\nБаллы: %d", lesson_n, lesson_b));
            }catch (Exception q){
                i-=1;
                ret_list.add(String.format("Предмет: %s\nБаллы: 0", lesson_n));
            }
        }

        return ret_list;
    }

    public List<String> TimeTable(String name, String pass){
        List<String> ret_list = new ArrayList<>();

        List<HtmlElement> elements = getPage("https://sdo.rgsu.net/timetable/student", name, pass).getByXPath("//tr");

        for(int i = 2; i < elements.size(); i++){
            ret_list.add(elements.get(i).asNormalizedText());
        }
        return ret_list;
    }

    private HtmlPage getPage(String url, String name, String pass){
        HtmlPage Authorization_page = null;
        try {
            WebClient Client = new WebClient();

            Client.getOptions().setCssEnabled(false);
            Client.getOptions().setJavaScriptEnabled(false);

            Authorization_page = Client.getPage("https://sdo.rgsu.net/index/authorization/role/guest/mode/view/name/Authorization");

            HtmlForm Form = Authorization_page.getForms().get(0);
            HtmlSubmitInput submitInput = Form.getInputByName("submit");

            HtmlTextInput login = Form.getInputByName("login");
            HtmlPasswordInput password = Form.getInputByName("password");

            login.setValueAttribute(name);
            password.setValueAttribute(pass);
            submitInput.click();

            Authorization_page = Client.getPage(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Authorization_page;
    }
}

