package ch5;

//import jdk.internal.org.jline.reader.Widget;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class IterativeWithoutSynchronization {
    //List<Widget> widgetList;

    //public IterativeWithoutSynchronization(List<Widget> widgetList) {
    //  this.widgetList = Collections.synchronizedList(widgetList);
    //}

    //UNSAFE:
    public void iterate() {
        //for (Widget w : widgetList) {
        //   doSomething(w);
    }
}

    /*public void safeIterate() {
        synchronized (widgetList) {
            for (Widget w : widgetList) {
                doSomething(w);
            }
        }
    }*/

//private void doSomething(Widget w) {
// ...
//}

