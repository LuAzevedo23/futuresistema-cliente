package com.devlu.futuresistema_cliente.utils;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;

public class GeracaoPDF {

    public static void gerarPDF(String conteudo, String nomeArquivo) {
        try {
            File file = new File(nomeArquivo + ".pdf");
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph(conteudo));

            document.close();
            Notificacao.exibirMensagem("Sucesso", "PDF Gerado",
                    "O PDF foi gerado com sucesso!", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            Notificacao.exibirMensagem("Erro", "Erro ao Gerar PDF",
                    "Ocorreu um erro ao gerar o PDF.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
}
