/**
 * Created by tomasz_taw
 * Date: 22.10.2023
 * Time: 14:20
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
@RequestMapping(CompController.COMP)
public class CompController {

    public static final String COMP = "/comp";

    private int secretNumber;
    private boolean focusNewGameButton;

    @Autowired
    private GuessHistory guessHistory;

    @GetMapping
    public String newPageGamePage(Model model) {
        secretNumber = generateRandomNumber();
        guessHistory.resetGuesses();
        boolean gameEnded = false;
        model.addAttribute("gameEnded", gameEnded);
        model.addAttribute("message", "Zgadnij liczbę od 1 do 100.");
        return "newPageGame";
    }

    @PostMapping
    public String newCheckGuess(@RequestParam("guess") int guess, Model model) {
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
        }
        model.addAttribute("history", guessHistory.getGuesses());
        return "newPageGame";
    }

    @PostMapping("/new-game")
    public String newNewGame() {
        secretNumber = generateRandomNumber();
        guessHistory.resetGuesses();
        return "redirect:/comp";
    }




    private int generateRandomNumber() {
        return new Random().nextInt(100) + 1;
    }



}
