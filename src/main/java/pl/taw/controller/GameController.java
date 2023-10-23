/**
 * Created by tomasz_taw
 * Date: 22.10.2023
 * Time: 12:49
 * Project Name: guessTheNumber
 * Description:
 */
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
@RequestMapping(GameController.GAME)
public class GameController {

    public static final String GAME = "/game";

    private int secretNumber;
    private boolean focusNewGameButton;

    @Autowired
    private GuessHistory guessHistory;

    @GetMapping
    public String gamePage(Model model) {
        secretNumber = generateRandomNumber();
        guessHistory.resetGuesses();
        boolean gameEnded = false;
        model.addAttribute("gameEnded", gameEnded);
        model.addAttribute("message", "Zgadnij liczbę od 1 do 100.");
        return "game";
    }

    @PostMapping
    public String checkGuess(@RequestParam("guess") int guess, Model model) {
        boolean gameEnded = false;
        guessHistory.addGuesses(guess);
        if (guess < secretNumber) {
            model.addAttribute("message", "Liczba jest większa.");
            model.addAttribute("gameEnded", gameEnded);
        } else if (guess > secretNumber) {
            model.addAttribute("message", "Liczba jest mniejsza.");
            model.addAttribute("gameEnded", gameEnded);
        } else {
            gameEnded = true;
            focusNewGameButton = true;
            model.addAttribute("gameEnded", gameEnded);
            model.addAttribute("focusNewGameButton", focusNewGameButton);
            model.addAttribute("message", "Brawo, zgadłeś liczbę!");



            int score = calculateScore(guessHistory.getGuesses().size()); // Funkcja, która oblicza wynik gry
            model.addAttribute("score", score);

        }
        model.addAttribute("history", guessHistory.getGuesses());
        return "game";
    }

    @PostMapping("/new-game")
    public String newGame() {
        secretNumber = generateRandomNumber();
        guessHistory.resetGuesses();
        return "redirect:/game";
    }



    // TODO do zrobienia podsumowanie gry, może lepiej to będzie zrobić na tej samej stronie

    @GetMapping("/end-game")
    public String endGame(Model model, Integer numberOfGuests) {
        int score = calculateScore(numberOfGuests);

        model.addAttribute("message", "Gra zakończona. Twój wynik to: " + score);

        return "endGame";
    }

    private int calculateScore(Integer numberOfGuests) {

        int score = 100 - (numberOfGuests * 5);

        return score;
    }

    private int generateRandomNumber() {
        return new Random().nextInt(100) + 1;
    }

}
