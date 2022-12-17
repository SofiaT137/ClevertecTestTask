package by.clevertec.service.reader;

import by.clevertec.service.dto.CashReceiptDto;
import by.clevertec.service.dto.CashReceiptItemDto;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.itextpdf.io.font.constants.StandardFonts.TIMES_BOLD;
import static com.itextpdf.io.font.constants.StandardFonts.TIMES_ROMAN;
import static com.itextpdf.layout.borders.Border.NO_BORDER;
import static com.itextpdf.layout.properties.TextAlignment.CENTER;

@Component
public class PdfWriter {
    private static final String PATH = "cashReceipt.pdf";
    private static final String CASHIER_ID = "Cashier Id: %s";
    private static final String CASH_RECEIPT = "CASH RECEIPT";
    private static final String TOTAL = "Total";
    private static final String PRICE = "Price";
    private static final String DESCRIPTION = "Description";
    private static final String QUANTITY = "Qty";
    private static final String DATE_AND_TIME = "Date: %s \n Time: %s ";
    private static final String YOUR_DISCOUNT = "Your total discount is ";
    private static final String CARD_NUMBER_AND_DISCOUNT = "Card # %d .Card discount: ";
    private static final String $_VALUE = "$%e";
    private static final int WIDTH_18 = 18;
    private static final int WIDTH_12 = 12;
    private static final int WIDTH_10 = 10;
    private static final int WIDTH_8 = 8;
    private static final float[] TABLE_WITH_TWO_COLUMN_WIDTH = {200f, 200f};
    private static final float[] TABLE_WITH_CASH_RECEIPT_ITEM_DATA_COLUMN_WIDTH = {50f, 150f, 100f, 100f};
    private static final String WRAP_TO_THE_NEW_LINE = "\n";

    public void createPdfFile(CashReceiptDto cashReceiptDto) throws IOException {
        PdfDocument pdfDocument = new PdfDocument(new com.itextpdf.kernel.pdf.PdfWriter(PATH));
        Paragraph paragraph = new Paragraph();
        pdfDocument.addNewPage();
        paragraph.add(getIntoAsCenterText(CASH_RECEIPT, TIMES_BOLD, WIDTH_18));
        paragraph.add(getIntoAsCenterText(cashReceiptDto.getShopDto().getShopName(), TIMES_ROMAN, WIDTH_10));
        paragraph.add(getIntoAsCenterText(cashReceiptDto.getShopDto().getShopAddress(), TIMES_ROMAN, WIDTH_10));
        paragraph.add(getIntoAsCenterText(cashReceiptDto.getShopDto().getShopPhone(), TIMES_ROMAN, WIDTH_10));
        paragraph.add(getTableWithTwoCells(String.format(CASHIER_ID, cashReceiptDto.getCashierDto().getCashierId()),
                String.format(DATE_AND_TIME, cashReceiptDto.getCashReceiptDate(), cashReceiptDto.getCashReceiptTime())
        ));
        paragraph.add(getTableWithCashReceiptItemData(cashReceiptDto.getCashReceiptItemList()));
        paragraph.add(getTableWithTwoCells(YOUR_DISCOUNT,
                String.format($_VALUE, cashReceiptDto.getTotalDiscountPercent())));
        if (Objects.nonNull(cashReceiptDto.getDiscountCardDto())) {
            paragraph.add(getTableWithTwoCells(String.format(CARD_NUMBER_AND_DISCOUNT,
                            cashReceiptDto.getDiscountCardDto().getId()),
                    String.valueOf(cashReceiptDto.getDiscountCardDto().getCardDiscountPercent())));
        }
        paragraph.add(getTableWithTwoCells(TOTAL, String.valueOf(cashReceiptDto.getTotalPrice())));
        paragraph.setTextAlignment(CENTER);
        Document document = new Document(pdfDocument);
        document.add(paragraph);
        document.close();
    }

    private Text getIntoAsCenterText(String content, String font, int fontSize) throws IOException {
        return new Text(content + WRAP_TO_THE_NEW_LINE)
                .setFont(PdfFontFactory.createFont(font))
                .setFontSize(fontSize)
                .setTextAlignment(CENTER);
    }

    private Table getTableWithTwoCells(String firstCellContent, String secondCellContent) {
        Table table = new Table(TABLE_WITH_TWO_COLUMN_WIDTH);
        Cell cell1 = getNewCellWithOutBorders(firstCellContent, WIDTH_8);
        Cell cell2 = getNewCellWithOutBorders(secondCellContent, WIDTH_8);
        table.addCell(cell1);
        table.addCell(cell2);
        return table;
    }

    private Cell getNewCellWithOutBorders(String cellContent, int fontSize) {
        return new Cell()
                .add(new Paragraph(cellContent).setFontSize(fontSize))
                .setBorder(NO_BORDER);
    }

    private Table getTableWithCashReceiptItemData(List<CashReceiptItemDto> cashReceiptItemList) {
        Table table = new Table(TABLE_WITH_CASH_RECEIPT_ITEM_DATA_COLUMN_WIDTH);
        table.addCell(getNewCellWithOutBorders(QUANTITY, WIDTH_12));
        table.addCell(getNewCellWithOutBorders(DESCRIPTION, WIDTH_12));
        table.addCell(getNewCellWithOutBorders(PRICE, WIDTH_12));
        table.addCell(getNewCellWithOutBorders(TOTAL, WIDTH_12));

        for (CashReceiptItemDto cashReceiptItem : cashReceiptItemList) {
            table.addCell(getNewCellWithOutBorders(String.valueOf(cashReceiptItem.getProductQuantity()), WIDTH_10));
            table.addCell(getNewCellWithOutBorders(String.valueOf(cashReceiptItem.getProductName()), WIDTH_10));
            table.addCell(getNewCellWithOutBorders(String.valueOf(cashReceiptItem.getProductPrice()), WIDTH_10));
            table.addCell(getNewCellWithOutBorders(String.valueOf(cashReceiptItem.getProductTotalPrice()), WIDTH_10));
        }
        return table;
    }
}
