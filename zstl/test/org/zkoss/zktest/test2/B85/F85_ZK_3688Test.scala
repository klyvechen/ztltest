import org.junit.Test
import org.zkoss.zstl.ZTL4ScalaTestCase

/* B85_ZK_3688Test.java

        Purpose:
                
        Description:
                
        History:
                Fri Jun 01 12:33:27 CST 2018, Created by klyve

Copyright (C) 2018 Potix Corporation. All Rights Reserved.
*/


class F85_ZK_3688Test extends ZTL4ScalaTestCase {
  @Test
  def test() = {
    runZTL(() => {
      //step 1 2
      for (i <- 1 to 3) {
        click(jq("$btn" + i))
        waitResponse();
      }
      for (i <- 1 to 2) {
        verifyTrue(zIndex(jq("$win" + i).toElement) < zIndex(jq("$win" + (i + 1)).toElement))
      }
      //step 3
      for (i <- 1 to 2) {
        click(jq("$win" + i))
        waitResponse();
        verifyTrue(zIndex(jq("$win" + i).toElement) > zIndex(jq("$win" + (i + 1)).toElement))
      }
      click(jq("$win3"))
      waitResponse();
      verifyTrue(zIndex(jq("$win3").toElement) > zIndex(jq("$win2").toElement))
      //step 4 5
      click(jq("$btn4"))
      waitResponse();
      verifyTrue(zIndex(jq("$win3").toElement) < zIndex(jq("$win2").toElement))
      verifyTrue(zIndex(jq("$win3").toElement) < zIndex(jq("$win1").toElement))
      //step 6 7
      click(jq("$btn5"))
      waitResponse();
      verifyTrue(zIndex(jq("$win3").toElement) > zIndex(jq("$win2").toElement))
      verifyTrue(zIndex(jq("$win3").toElement) > zIndex(jq("$win1").toElement))
      //step 8
      click(jq("$win1"))
      waitResponse();
      verifyTrue(zIndex(jq("$win1").toElement) > zIndex(jq("$win2").toElement))
      verifyTrue(zIndex(jq("$win1").toElement) > zIndex(jq("$win3").toElement))
      click(jq("$win2"))
      waitResponse();
      verifyTrue(zIndex(jq("$win2").toElement) > zIndex(jq("$win1").toElement))
      verifyTrue(zIndex(jq("$win2").toElement) > zIndex(jq("$win3").toElement))
      click(jq("$win3"))
      waitResponse();
      verifyTrue(zIndex(jq("$win2").toElement) > zIndex(jq("$win1").toElement))
      verifyTrue(zIndex(jq("$win2").toElement) > zIndex(jq("$win3").toElement))
    })
  }
  def zIndex(out: org.zkoss.ztl.Element): Int = {
    var result = out.eval("style.zIndex"); 
    return if (result == null || result.equals("")) -1 else result.toInt;
  }
}