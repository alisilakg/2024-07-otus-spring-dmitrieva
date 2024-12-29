package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        ioService.printLine("");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);
        int questionCounter = 1;

        for (var question : questions) {
            if (Objects.nonNull(question)) {
                ioService.printLine("");
                ioService.printFormattedLine("Question %d: %s", questionCounter++, question.text());
                var answers = question.answers();
                var rightAnswer = getRightAnswerNumber(answers);
                int answerStudent = ioService.readIntForRangeWithPrompt(
                        1,
                        answers.size() - 1,
                        "Enter the correct answer number:",
                        "You entered a number that doesn't exist. Try again");
                testResult.applyAnswer(question, Objects.equals(answerStudent, rightAnswer));
            }
        }
        return testResult;
    }

    private int getRightAnswerNumber(List<Answer> answers) {
        int answerCounter = 1;
        int rightAnswer = 0;
        for (Answer answer : answers) {
            if (Objects.nonNull(answer)) {
                ioService.printFormattedLine("Answer %d: %s", answerCounter++, answer.text());
                ioService.printLine("");
            }
            if (answer.isCorrect()) {
                rightAnswer = answerCounter - 1;
            }
        }
        return rightAnswer;
    }
}
