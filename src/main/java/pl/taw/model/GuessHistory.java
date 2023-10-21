package pl.taw.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuessHistory {

    private List<Integer> guesses = new ArrayList<>();

    public void addGuesses(int guess) {
        guesses.add(guess);
    }

    public void resetGuesses() {
        guesses = new ArrayList<>();
    }
}
