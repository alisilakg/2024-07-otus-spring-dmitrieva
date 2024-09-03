package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class TestServiceImplTest {

    private IOService mockIOService;
    private CsvQuestionDao mockQuestionDao;
    private TestService testService;

    @BeforeEach
    void setUp() {
        mockIOService = Mockito.mock(StreamsIOService.class);
        mockQuestionDao = Mockito.mock(CsvQuestionDao.class);
        testService = new TestServiceImpl(mockIOService, mockQuestionDao);
    }

    @Test
    void executeTest_QuestionsAndAnswersAreNotNull_PrintQuestionsAndAnswers() {
        final Question question1 = new Question("something question 1", List.of(
                new Answer("something answer 1", Boolean.TRUE)));
        final List<Question> questions = new ArrayList<>(List.of(question1));
        when(mockQuestionDao.findAll()).thenReturn(questions);

        testService.executeTest();

        verify(mockIOService, times(3)).printLine("");
        verify(mockIOService).printFormattedLine("Please answer the questions below%n");
        verify(mockIOService).printFormattedLine("Question %d: %s", 1, "something question 1");
        verify(mockIOService).printFormattedLine("Answer %d: %s", 1, "something answer 1");
    }
}
