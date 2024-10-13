package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CsvQuestionDaoTest {

    private TestFileNameProvider testFileNameProvider;

    private QuestionDao questionDao;

    @BeforeEach
    public void setUp() {
        testFileNameProvider = mock(TestFileNameProvider.class);
        questionDao = new CsvQuestionDao(testFileNameProvider);
    }

    @Test
    public void findAllQuestion_whenFileExists_thenCorrect() {
        var expectedQuestions = List.of(new Question("something question 1", List.of(
                new Answer("something answer 1", false),
                new Answer("something answer 2", true))));
        when(testFileNameProvider.getTestFileName()).thenReturn("test.csv");

        var actualQuestions = questionDao.findAll();

        assertEquals(expectedQuestions, actualQuestions);
    }

    @Test
    public void findAllQuestion_whenFileDoesNotExist_thenThrowQuestionReadException() {
        when(testFileNameProvider.getTestFileName()).thenReturn("wrongPath.csv");

        assertThrows(QuestionReadException.class, () -> questionDao.findAll());
    }
}