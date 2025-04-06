package org.example.api.controllers.v1.parser;

import org.example.api.controllers.v1.dto.BatchRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TxtBatchParser {

    public BatchRequest parseAndValidate(MultipartFile file) throws IOException {
        List<String> lines = new BufferedReader(new InputStreamReader(file.getInputStream()))
                .lines()
                .toList();

        if (lines.size() < 3) {
            throw new IllegalArgumentException("Arquivo deve conter ao menos 3 linhas (cabeçalho, dados, rodapé)");
        }

        String header = lines.get(0);
        String footer = lines.get(lines.size() - 1);
        List<String> cardLines = lines.subList(1, lines.size() - 1);

        String name = header.substring(0, 29).trim();
        String dateStr = header.substring(29, 37).trim();
        String batchCode = header.substring(37, 45).trim();
        int expectedCount = Integer.parseInt(header.substring(45, 51).trim());

        LocalDate batchDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            batchDate = LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data do cabeçalho inválida: " + dateStr + ". Use o formato yyyyMMdd.");
        }

        if (cardLines.size() != expectedCount) {
            throw new IllegalArgumentException("Quantidade de cartões não corresponde ao informado no cabeçalho.");
        }

        String footerBatch = footer.substring(0, 8).trim();
        int footerCount = Integer.parseInt(footer.substring(8, 14).trim());

        if (!footerBatch.equals(batchCode)) {
            throw new IllegalArgumentException("Código do lote no rodapé não bate com o do cabeçalho.");
        }
        if (footerCount != expectedCount) {
            throw new IllegalArgumentException("Quantidade de cartões no rodapé não bate com o do cabeçalho.");
        }

        List<BatchRequest.CardInput> cardInputs = getCardInputs(cardLines);

        BatchRequest request = new BatchRequest();
        request.setName(name);
        request.setBatchCode(batchCode);
        request.setBatchDate(batchDate);
        request.setCards(cardInputs);

        return request;
    }

    private static List<BatchRequest.CardInput> getCardInputs(List<String> cardLines) {
        List<BatchRequest.CardInput> cardInputs = new ArrayList<>();
        for (String line : cardLines) {
            if (line.length() < 26)
                throw new IllegalArgumentException("Linha de cartão inválida: " + line);

            String lineId = line.substring(0, 1);
            int order = Integer.parseInt(line.substring(1, 7).trim());
            String cardNumber = line.substring(7, 26).trim();

            BatchRequest.CardInput card = new BatchRequest.CardInput();
            card.setLineIdentifier(lineId);
            card.setOrderInBatch(order);
            card.setCardNumber(cardNumber);
            cardInputs.add(card);
        }
        return cardInputs;
    }
}
