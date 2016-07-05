
package myapp.tae.ac.uk.simplegameinfo.Model;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
public class Games implements Parcelable
{
    private String response;
    private String currency;
    private List<Data> data = new ArrayList<Data>();
    public final static Creator<Games> CREATOR = new Creator<Games>() {


        public Games createFromParcel(Parcel in) {
            Games instance = new Games();
            instance.response = ((String) in.readValue((String.class.getClassLoader())));
            instance.currency = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.data, (Data.class.getClassLoader()));
            return instance;
        }

        public Games[] newArray(int size) {
            return (new Games[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The response
     */
    public String getResponse() {
        return response;
    }

    /**
     * 
     * @param response
     *     The response
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * 
     * @return
     *     The currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 
     * @param currency
     *     The currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 
     * @return
     *     The data
     */
    public List<Data> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Data> data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(response);
        dest.writeValue(currency);
        dest.writeList(data);
    }

    public int describeContents() {
        return  0;
    }

}
