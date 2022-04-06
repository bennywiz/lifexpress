package life.example.xpress;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class mysqlhelper {

    private static mysqlhelper mInstance;
    private RequestQueue requestQueue;
    private static Context mcontext;

    private mysqlhelper(Context context){

        mcontext = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(mcontext.getApplicationContext());

        }
        return requestQueue;
    }

    public static synchronized mysqlhelper getInstance(Context context){

        if (mInstance == null){
            mInstance = new mysqlhelper(context);
        }
        return  mInstance;
    }

    public <T> void  addToRequestQueue(Request<T> request){
       getRequestQueue().add(request);
    }

   /* public void setRequestQueue(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }*/
}
