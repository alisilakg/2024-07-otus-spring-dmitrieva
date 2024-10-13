package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Student student = new Student("Ivan", "Ivanov");

        var expectedQuestions = List.of(new Question("something question 1", List.of(
                new Answer("something answer 1", false),
                new Answer("something answer 2", true))));

        when(mockQuestionDao.findAll()).thenReturn(expectedQuestions);
        when(mockIOService.readIntForRangeWithPrompt(anyInt(), anyInt(), anyString(), anyString())).thenReturn(1);

        var actual = testService.executeTestFor(student);

        assertEquals(expectedQuestions, actual.getAnsweredQuestions());

        verify(mockIOService, times(5)).printLine("");
        verify(mockIOService).printFormattedLine("Please answer the questions below%n");
        verify(mockIOService).printFormattedLine("Question %d: %s", 1, "something question 1");
        verify(mockIOService).printFormattedLine("Answer %d: %s", 1, "something answer 1");
        verify(mockIOService).printFormattedLine("Answer %d: %s", 2, "something answer 2");
    }
}