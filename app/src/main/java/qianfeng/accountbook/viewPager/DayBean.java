package qianfeng.accountbook.viewPager;

/**
 * Created by Administrator on 2016/9/24 0024.
 */
public class DayBean  {


    /**
     * Created by Administrator on 2016/9/23 0023.
*/

        private String get;
        private String pain;
        private String comm;
        private String date;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getGet() {
            return get;
        }

        public void setGet(String get) {
            this.get = get;
        }

        public String getPain() {
            return pain;
        }

        public void setPain(String pain) {
            this.pain = pain;
        }

        public String getComm() {
            return comm;
        }

        public void setComm(String comm) {
            this.comm = comm;
        }



        @Override
        public String toString() {
            return "Accout{" +
                    ", get='" + get + '\'' +
                    ", pain='" + pain + '\'' +
                    ", comm='" + comm + '\'' +
                    ", date='" + date + '\'' +
                    '}';
        }
    }


