package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        ClassLoader classLoader = getClass().getClassLoader();
        String fileName = fileNameProvider.getTestFileName();

        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            if (Objects.isNull(inputStream)) {
                throw new QuestionReadException("file not found! " + fileName);
            }
            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
                return new CsvToBeanBuilder<QuestionDto>(inputStreamReader)
                        .withType(QuestionDto.class)
                        .withSeparator(';')
                        .withSkipLines(1)
                        .build()
                        .parse()
                        .stream()
                        .map(QuestionDto::toDomainObject).toList();
            }
        } catch (IOException e) {
            throw new QuestionReadException("file not read! " + fileName, e);
        }
    }
}
