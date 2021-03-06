package org.zkoss.zktest.test2.F85

import org.junit.Test
import org.zkoss.zstl.ZTL4ScalaTestCase
import org.zkoss.ztl.annotation.Tags

/**
  * @author rudyhuang
  */
@Tags(tags = "F85-ZK-3739.zul")
class F85_ZK_3739Test extends ZTL4ScalaTestCase {
  @Test
  def test()=  {
    runZTL(() => {
      val textbox = jq("@textbox")
      focus(textbox)
      zk(textbox).eval("setSelectionRange(2,2);'test'")

      click(jq("@toolbarbutton:eq(0)"))
      waitResponse()
      click(jq("@toolbarbutton:eq(3)"))
      waitResponse()

      // Check client
      verifyEquals("==SELECT DISTINCT ======================", textbox.`val`())

      click(jq("@button"))
      waitResponse()

      // Check server
      verifyEquals("==SELECT DISTINCT ======================", getZKLog())
    })
  }
}
