package com.supportportal.enumeration;

public enum TASKETAT {

      TASKETAT_INPROGRESS,
       TASKETAT_TOFINISH,
       TASKETAT_UNSTARTED,
       TASKETAT_CANCEL;
        private String [] taskEtat;

    TASKETAT(String...taskEtat){
            this.taskEtat = taskEtat;
        }

        public String[] getTaskEtat(){
            return taskEtat;
        }


}
