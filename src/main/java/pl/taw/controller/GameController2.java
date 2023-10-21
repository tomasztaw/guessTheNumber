package pl.taw.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.taw.model.GuessHistory;

import java.util.Random;

@Controller
@NoArgsConstructor
@AllArgsConstructor
@RequestMapping(GameController2.GAME)
public class GameController2 {

    public static final String GAME = "/game2";

    private int secretNumber;

    @Autowired
    private GuessHistory guessHistory;

    @GetMapping
    public String gamePage2(Model model) {
        secretNumber = generateRandomNumber();
        guessHistory.resetGuesses();
        model.addAttribute("message", "Zgadnij liczbę od 1 do 100.");
        return "game2";
    }

    @PostMapping
    public String checkGuess2(@RequestParam("guess") int guess, Model model) {
        guessHistory.addGuesses(guess);
        if (guess < secretNumber) {
            model.addAttribute("message", "Liczba jest większa.");
        } else if (guess > secretNumber) {
            model.addAttribute("message", "Liczba jest mniejsza.");
        } else {
            model.addAttribute("message", "Brawo, zgadłeś liczbę!");
        }
        model.addAttribute("history", guessHistory.getGuesses());
        return "game2";
    }

    @PostMapping("/new-game")
    public String newGame() {
        secretNumber = generateRandomNumber();
        guessHistory.resetGuesses();
        return "redirect:/game2";
    }



    // TODO do zrobienia podsumowanie gry, może lepiej to będzie zrobić na tej samej stronie

    @GetMapping("/end-game")
    public String endGame(Model model) {
        int score = calculateScore();


        model.addAttribute("message", "Gra zakończona. Twój wynik to: " + score);


        return "endGame";
    }

    private int calculateScore() {


        int score = 100;

        return score;
    }

    private int generateRandomNumber() {
        return new Random().nextInt(100) + 1;
    }

}
