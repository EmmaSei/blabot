package com.bla;

import com.bla.entities.Car;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.ForwardMessage;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bot extends TelegramLongPollingBot {
    private Car car = new Car();
    private ArrayList<String> txtsOfBtns = new ArrayList<>();
    private HashMap<String, String> inlineBtns = new HashMap<>();

    public static void main(String[] args) {
        ApiContextInitializer.init(); // Инициализируем апи
        TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для приема сообщений.
     * @param update Содержит сообщение от пользователя.
     */
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            Message msg = update.getMessage();
            String txt = msg.getText();
//            msg.getFrom().getId();

            switch (txt) {
                case "/start" : {
                        txtsOfBtns.clear();
                        inlineBtns.clear();
                        txtsOfBtns.add("Предложить поездку");
                        txtsOfBtns.add("Найти поездку");
                        sendMsg(msg, "Hello, world! This is your stupid bot!");
                        answerCallbackQuery(update.getCallbackQuery().getId(), "Blya!");
                        break;
                    }
                case "Предложить поездку": {
                        // TODO: check cars info
                        boolean firstTrip = true;
                        if (firstTrip) {
                            // TODO: include library of cars brands
                            ArrayList<String> listOfBrands = new ArrayList<>();

                            //TODO remove this mock
                            {
                                listOfBrands.add("Mersedes");
                                listOfBrands.add("BMW");
                                listOfBrands.add("Audi");
                                listOfBrands.add("Toyota");
                            }

                            txtsOfBtns.clear();
                            inlineBtns.clear();
                            listOfBrands.forEach(brand -> txtsOfBtns.add(brand));

                            sendMsg(msg, "Первая поездка! Расскажи о своей машине. Какой она Марки?");
                            ForwardMessage forwardMessage = new ForwardMessage();
                            try {
                                Message msgF = forwardMessage(forwardMessage);
                                sendMsg(msgF, "qwer");
                            } catch (TelegramApiException e1) {
                                e1.printStackTrace();
                            } finally {
                                break;
                            }
                        }
                    }
                case "Найти поездку" : {
                        sendMsg(msg, "Уже ищу!");
                        break;
                    }
                case "/number" : {
                        car.setNumber(update.toString());
                        sendMsg(msg, car.toString());
                        break;
                    }
                case "/marka" : {
                        sendMsg(msg, car.toString());
                        break;
                    }
            }
        }
    }


    /**
     * Метод возвращает имя бота, указанное при регистрации.
     * @return имя бота
     */
    @Override
    public String getBotUsername() {
        return "blablarnd_Bot";
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     * @return token для бота
     */
    @Override
    public String getBotToken() {
        return "623664359:AAF616iuDKmdZWOcHL56P2sgbbwxY5LZsgg";
    }

    @SuppressWarnings("deprecation") // Означает то, что в новых версиях метод уберут или заменят
    private void sendMsg(Message msg, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(msg.getChatId()); // Боту может писать не один человек, и поэтому чтобы отправить сообщение, грубо говоря нужно узнать куда его отправлять
        sendMessage.setReplyToMessageId(msg.getMessageId());
        sendMessage.setText(text);
        setButtons(sendMessage, txtsOfBtns);
        setInline(sendMessage, inlineBtns);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
    public synchronized void setButtons(SendMessage sendMessage, ArrayList<String> txtsOfBtns) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();


        txtsOfBtns.forEach(lbl -> {
            KeyboardRow instanceKeyboardRow = new KeyboardRow();
            instanceKeyboardRow.add(new KeyboardButton(lbl));
            keyboard.add(instanceKeyboardRow);
        });
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    private void setInline(SendMessage sendMessage, Map<String, String> inlineBtns) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        sendMessage.setReplyMarkup(markupKeyboard);
        inlineBtns.forEach((key, value) -> {
            List<InlineKeyboardButton> button = new ArrayList<>();
            button.add(new InlineKeyboardButton().setText(value).setCallbackData(key));
            buttons.add(button);
        });

        markupKeyboard.setKeyboard(buttons);
    }

    public synchronized void answerCallbackQuery(String callbackId, String message) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(callbackId);
        answer.setText(message);
        answer.setShowAlert(true);
        try {
            answerCallbackQuery(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}