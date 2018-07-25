inimport java.io.IOException;
import java.util.Map;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;

public class InteractivePDFfilling {

	public static void main(String[] args) throws IOException {
		
// http://foersom.com/net/HowTo/data/OoPdfFormExample.pdf
// https://mvnrepository.com/artifact/com.itextpdf/kernel 
//  https://mvnrepository.com/artifact/com.itextpdf/forms
//   https://mvnrepository.com/artifact/com.itextpdf/pdftest
		String originalPdf = "D:\\interactivePDFs\\OoPdfFormExample.pdf";
		String targetPdf = "D:\\interactivePDFs\\OoPdfFormExample_filled.pdf";
	
        PdfDocument pdf = new PdfDocument(new PdfReader(originalPdf), new PdfWriter(targetPdf));
 		
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
        Map<String, PdfFormField> fields = form.getFormFields();
        fields.get("Given Name Text Box").setValue("Venkateshwara");
        fields.get("Family Name Text Box").setValue("Doijode");
        fields.get("House nr Text Box").setValue("120");
        fields.get("Address 2 Text Box").setValue("yyyyyy");
        fields.get("Postcode Text Box").setValue("600049");
        fields.get("Address 1 Text Box").setValue("zzzzzzz");
        fields.get("Gender List Box").setValue("Woman");
        fields.get("Country Combo Box").setValue("France");
        fields.get("Height Formatted Field").setValue("200");
        fields.get("City Text Box").setValue("Chennai");
        fields.get("Driving License Check Box").setValue("Yes");
        fields.get("Favourite Colour List Box").setValue("Blue");
        fields.get("Language 1 Check Box").setValue("Yes");
        fields.get("Language 2 Check Box").setValue("Off");
        fields.get("Language 3 Check Box").setValue("Yes");
        fields.get("Language 4 Check Box").setValue("Off");
        fields.get("Language 5 Check Box").setValue("Yes");
        pdf.close();
		System.out.println("completed");
	}

}
