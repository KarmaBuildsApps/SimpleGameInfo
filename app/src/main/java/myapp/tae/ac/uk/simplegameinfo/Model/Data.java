
package myapp.tae.ac.uk.simplegameinfo.Model;

import android.os.Parcel;
import android.os.Parcelable;
public class Data implements Parcelable
{

    private String name;
    private Integer jackpot;
    private String date;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        public Data createFromParcel(Parcel in) {
            Data instance = new Data();
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.jackpot = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.date = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The jackpot
     */
    public Integer getJackpot() {
        return jackpot;
    }

    /**
     * 
     * @param jackpot
     *     The jackpot
     */
    public void setJackpot(Integer jackpot) {
        this.jackpot = jackpot;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(jackpot);
        dest.writeValue(date);
    }

    public int describeContents() {
        return  0;
    }

}
