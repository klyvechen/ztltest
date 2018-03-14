package org.zkoss.zktest.test2.B85

import org.junit.Test
import org.zkoss.zstl.ZTL4ScalaTestCase

/* B85_ZK_3795Test.java

        Purpose:
                
        Description:
                
        History:
                Wed Mar 14 11:37 AM:29 CST 2018, Created by klyve

Copyright (C) 2018 Potix Corporation. All Rights Reserved.
*/ class B85_ZK_3795Test extends ZTL4ScalaTestCase {
  @Test
  def test(): Unit = {
    runZTL(() => {
      verifyEquals(jq(".z-progressmeter-image").height(), jq(".z-progressmeter").height())
    })
  }
}
