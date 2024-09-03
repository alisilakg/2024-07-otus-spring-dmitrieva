package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;
    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        ioService.printLine("");
        final List<Question> questions = questionDao.findAll();
        printQuestions(questions);
    }

    private void printQuestions(List<Question> questions) {
        int questionCounter = 1;
        for (Question question : questions) {
            if (Objects.nonNull(question)) {
                ioService.printLine("");
                ioService.printFormattedLine("Question %d: %s", questionCounter++, question.text());
                printAnswers(question.answers());
            }
        }
    }

    private void printAnswers(List<Answer> answers) {
        int answerCounter = 1;
        for (Answer answer : answers) {
            if (Objects.nonNull(answer)) {
                ioService.printFormattedLine("Answer %d: %s", answerCounter++, answer.text());
            }
        }
    }
}
