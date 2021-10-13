public class Time {
    private int hour;
    private int minute;

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public String toString() {
        if (hour < 10 && minute < 10) {
            return "0" + hour + ":" + "0" + minute;
        } else if (hour < 10){
            return "0" + hour + ":" + minute;
        } else if (minute < 10){
            return hour + ":" + "0" + minute;
        } else {
            return hour + ":" + minute;
        }
    }

    public int sort(){
        return hour * 60 + minute;
    }
}
