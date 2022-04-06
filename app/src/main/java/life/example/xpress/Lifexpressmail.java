package life.example.xpress;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Lifexpressmail extends AsyncTask<Void ,Void ,Void>{

    private Context context;

    private Session session;
    String  subject, msg;
    SweetAlertDialog pDialog;

    public Lifexpressmail(Context context, String subject, String msg) {
        this.context = context;
      //  this.email = email;
        this.subject = subject;
        this.msg = msg;
        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog.setTitleText("Demande En cours...");
        pDialog.setCancelable(true);
        pDialog.show();
        System.out.println("votre demande encours...");

       // Toast.makeText(context, , Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);
        pDialog.cancel();
        SweetAlertDialog sw =new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        sw.setTitleText("demande envoyée");
        sw.setContentText("Vous serez contactez ultérieurement!");
        sw.show();
        System.out.println("Demande envoyée");
    }

    @Override
    protected Void doInBackground(Void... voids) {
        //TelephonyManager tMgr = (TelephonyManager) MainActivity.getSystemService(Context.TELEPHONY_SERVICE);
      //  String mPhoneNumber = tMgr.getLine1Number();

        final String to = "lifexpress@yahoo.com";
        final String from = "lifexpressclient@yahoo.com";
        String host = "smtp.mail.yahoo.com";
        Properties properties = System.getProperties();
//        session.getProperties().put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.ssl.trust", "smtp.yahoo.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("lifexpressclient@yahoo.com", "nuzvhugndwkteply");
            }
        });

        session.setDebug(true);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(msg);

            System.out.println("sending...");
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

        return null;
    }
}
