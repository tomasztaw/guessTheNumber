package pl.taw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.taw.model.GuessHistory;

import java.util.Random;

@Controller
@RequestMapping(GameController.GAME)
public class GameController {

    public static final String GAME = "/game";

    private int secretNumber;
    private GuessHistory guessHistory = new GuessHistory();


    @GetMapping
    public String gamePage(Model model) {
        secretNumber = generateRandomNumber();
        model.addAttribute("message", "Zgadnij liczbę od 1 do 100.");
        return "game";
    }

    @PostMapping
    public String checkGuess(@RequestParam("guess") int guess, Model model) {
        guessHistory.addGuesses(guess);
        if (guess < secretNumber) {
            model.addAttribute("message", "Liczba jest większa.");
        } else if (guess > secretNumber) {
            model.addAttribute("message", "Liczba jest mniejsza.");
        } else {
            model.addAttribute("message", "Brawo, zgadłeś liczbę!");
        }
        model.addAttribute("guessesHistory", guessHistory);
        return "game";
    }

    private int generateRandomNumber() {
        return new Random().nextInt(100) + 1;
    }
}
