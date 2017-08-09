package id.renaldirey.createreadpdf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.File;

public class PDFView extends AppCompatActivity {
    com.github.barteksc.pdfviewer.PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);
        pdfView = (com.github.barteksc.pdfviewer.PDFView) findViewById(R.id.pdfView);

        Bundle ekstrak = getIntent().getExtras();
        String url = ekstrak.getString("pathpdf");

        File file = new File(url);
        pdfView.fromFile(file).load();
    }
}
