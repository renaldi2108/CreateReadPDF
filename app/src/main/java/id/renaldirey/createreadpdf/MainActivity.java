package id.renaldirey.createreadpdf;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text)
    EditText konten;
    @BindView(R.id.openPDF)
    Button openpdf;
    @BindString(R.string.kontentext)
    String convert;
    @BindString(R.string.filepath)
    String path;

    File currentFile;
    public static final int REQUEST_CODE_PERMISSION_WRITE = 107;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        openpdf.setEnabled(false);
    }

    @OnClick(R.id.convrtPDF)
    void clickConvert(){
        convert = konten.getText().toString();
        if(!convert.isEmpty()){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION_WRITE);
            } else {
                generatePDF(convert);
            }
        } else{
            Toast.makeText(this, "Mohon diisi", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.openPDF)
    void clickOpened(){
        Intent intent = new Intent(this, PDFView.class);
        intent.putExtra("pathpdf", path);
        startActivity(intent);
    }

    private void generatePDF(String konten){

        File directoryFolder = new File(Environment.getExternalStorageDirectory(), "CreateReadPDF");
        if (!directoryFolder.exists()) {
            directoryFolder.mkdirs();
        }
        try {
            currentFile = new File(directoryFolder, "test.pdf");
            path = directoryFolder.getPath().toString()+"/test.pdf";

            if (!currentFile.exists()) {
                currentFile.createNewFile();
            }

            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);


            final Document document = new Document();

            PdfWriter.getInstance(document,
                    new FileOutputStream(currentFile.getAbsoluteFile()));
            document.open();
            document.add(new Paragraph(konten));
            document.close();
            openpdf.setEnabled(true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
