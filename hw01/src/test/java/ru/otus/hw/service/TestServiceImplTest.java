package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class TestServiceImplTest {
    @Test
    void testExecuteTestWhenQuestions() {
        final IOService mockIOService = Mockito.mock(IOService.class);
        final CsvQuestionDao mockQuestionDao = Mockito.mock(CsvQuestionDao.class);
        final TestServiceImpl testService = new TestServiceImpl(mockIOService, mockQuestionDao);
        final Question question1 = new Question("something question 1", List.of(new Answer("something answer 1", Boolean.TRUE)));
        final List<Question> questions = new ArrayList<>(List.of(question1));
        when(mockQuestionDao.findAll()).thenReturn(questions);

        testService.executeTest();

        verify(mockIOService, times(3)).printLine("");
        verify(mockIOService).printFormattedLine("Please answer the questions below%n");
        verify(mockIOService).printFormattedLine("Question %d: %s", 1, "something question 1");
        verify(mockIOService).printFormattedLine("Answer %d: %s", 1, "something answer 1");
    }
}
