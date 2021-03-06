/* B50_3322795Test.scala

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Tue Oct 18 18:30:35 CST 2011 , Created by benbai
}}IS_NOTE

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.zktest.test2.B50

import org.zkoss.zstl.ZTL4ScalaTestCase
import org.zkoss.ztl.annotation.Tags
import org.zkoss.ztl.unit.Widget

/**
  * A test class for bug 3322795
  *
  * @author benbai
  *
  */
@Tags(tags = "B50-3322795.zul,A,E,Doublespinner")
class B50_3322795Test extends ZTL4ScalaTestCase {

  def testClick() = {
    val zscript =
      """
			
			<zk>
				1. click on the right side up/down button several times
				<separator/>
				2. the value change is 1 each time, the value inside should be ... 9.7, 8.7, 7.7, 6.7,... 
				<separator/>
				
				<doublespinner id="ds" value="8.7" width="200px"></doublespinner>
			</zk>

    """
    runZTL(zscript,
      () => {
        var ds: Widget = engine.$f("ds");
        var value: Int = 8;
        for (i <- 1 until 5) {
          value += 1;
          click(ds.$n("btn-up"));
          waitResponse();

          verifyEquals("the value change is 1 each time",
            (value + ".7"), ds.$n("real").attr("value"))
        }
        for (j <- 1 until 10) {
          value = value - 1
          click(ds.$n("btn-down"));
          waitResponse()
          verifyEquals("the value change is 1 each time",
            (value + ".7"), ds.$n("real").attr("value"))
        }
      }
    );

  }
}