/* Z60_Grid_ListModelSet_PagingTest.scala

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Thu Jan 19 09:29:19 CST 2012 , Created by benbai
}}IS_NOTE

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.zktest.test2.Z60

import org.zkoss.zstl.ZTL4ScalaTestCase
import scala.collection.JavaConversions._
import org.junit.Test;
import org.zkoss.ztl.Element;
import org.zkoss.ztl.JQuery;
import org.zkoss.ztl.Tags;
import org.zkoss.ztl.util.Scripts;
import org.zkoss.ztl.Widget;
import org.zkoss.ztl.ZK;
import org.zkoss.ztl.ZKClientTestCase;
import java.lang._

/**
 * A test class for bug Grid-ListModelSet-Paging
 * @author benbai
 *
 */
@Tags(tags = "Z60-Grid-ListModelSet-Paging.zul,Z60,A,E,Grid,ListModelSet,Paging")
class Z60_Grid_ListModelSet_PagingTest extends ZTL4ScalaTestCase {
	@Test
  def testClick() = {
    val zscript = """
			<zk>
			    <zscript><![CDATA[
					import org.zkoss.zktest.test2.select.models.*;
					
					ListModelSet model = ListModelSets.getModel(ListModelSets.DEFAULT);
					ListModelSet model2 = ListModelSets.getModel(ListModelSets.CLONEABLE);
					
					int cnt = 0;
					int elemcnt = 1001;
					int rmcnts = 0;
					int rmcntm = 1001;
					
					// param: sort ascending
					Comparator asc = ListModelSets.getRowComparator(true);
					Comparator dsc = ListModelSets.getRowComparator(false);
			    ]]></zscript>
				<div>
					<div>1. There are 3 Grid below.</div>
					<div>2. For first two Grid, their sort status will sync automatically after you select item.</div>
					<div>3. Sort third grid in descending order.</div>
					<div>4. Click clone and 'clone by serialization', then two Grides should be created and also sorted in descending order.</div>
					<div>5. Sort third, fourth and fifth grid several times, the sort status should not sync.</div>
				</div>
				<hbox>
					<grid id="gridOne" height="150px" width="310px" mold="paging" pageSize="10" model="${model}" onSelect="">
				        <columns>
				            <column label="Category" sortAscending="${asc}" sortDescending="${dsc}" />
				        </columns>
				    </grid>
					
					<grid id="gridTwo" height="150px" width="310px" mold="paging" pageSize="10" model="${model}" onSelect="">
				        <columns>
				            <column label="Category" sortAscending="${asc}" sortDescending="${dsc}" />
				        </columns>
				    </grid>
				    <grid id="gridThree" height="150px" width="310px" mold="paging" pageSize="10" model="${model2}" onSelect="">
				         <columns>
				            <column label="Category" sortAscending="${asc}" sortDescending="${dsc}" />
				        </columns>
				    </grid>
				</hbox>
				<div height="10px"></div>
				<button id="clone" label="clone">
					<attribute name="onClick">
						Grid grid = gridThree.clone();
						grid.setId("gridThree_clone" + cnt++);
						grid.setParent(cloneThreeArea);
					</attribute>
				</button>
				<button id="serialize" label="Clone by Serialization">
					<attribute name="onClick"><![CDATA[{
						import java.io.*;
						ByteArrayOutputStream boa = new ByteArrayOutputStream();
						new ObjectOutputStream(boa).writeObject(gridThree);
						byte[] bs = boa.toByteArray();
						Object n = new ObjectInputStream(new ByteArrayInputStream(bs)).readObject();
						n.setId("gridThree_serialize" + cnt++);
						((Component)n).setParent(cloneThreeArea);
					}]]></attribute>
				</button>
				<button label="insert item">
					<attribute name="onClick">
						model.add("data "+elemcnt);
						model2.add("data "+elemcnt);
						elemcnt++;
					</attribute>
				</button>
				<button label="remove item">
					<attribute name="onClick"><![CDATA[
						String obj = "data ";
						if (rmcntm < elemcnt) {
							obj += rmcntm;
							rmcntm++;
						} else {
							obj += rmcnts;
							rmcnts++;
						}
						model.remove(obj);
						model2.remove(obj);
					]]></attribute>
				</button>
				<hbox id="cloneThreeArea" />
			</zk>

    """

    runZTL(zscript,
        () => {
        var clone: Widget = engine.$f("clone");
        var serialize: Widget = engine.$f("serialize");

        def sort (id: String, cate: String) {
          var grid: Widget = engine.$f(id);
          click(jq(grid.$n("head")).find(".z-columns").find(".z-column:contains("+cate+")"));
          waitResponse();
        }
        def isAscending (id: String): Boolean = {
          var grid: Widget = engine.$f(id);
          return jq(grid.$n("body")).find(".z-rows").find(".z-row-content:contains(data)").find("span").get(0).get("innerHTML").contains("0");
        }
        def checkNotSync(idOne: String, idTwo: String) = {
          var old: Boolean = isAscending(idTwo);
          for (i <- 0 to 3) {
            sort(idOne, "Category");
            verifyTrue("The sort status between last three grid should not sync",
            		isAscending(idTwo) == old);
          }
        }
        sort("gridOne", "Category");
        verifyTrue("Sort order between first two grid should the same",
            isAscending("gridOne") == isAscending("gridTwo"));
        sort("gridOne", "Category");
        verifyTrue("Sort order between first two grid should the same",
            isAscending("gridOne") == isAscending("gridTwo"));
        sort("gridTwo", "Category");
        verifyTrue("Sort order between first two grid should the same",
            isAscending("gridOne") == isAscending("gridTwo"));
        sort("gridTwo", "Category");
        verifyTrue("Sort order between first two grid should the same",
            isAscending("gridOne") == isAscending("gridTwo"));

        sort("gridThree", "Category");
        if (isAscending("gridThree")) sort("gridThree", "Category");
        click (clone);
        waitResponse();
        click (serialize);
        waitResponse();

        verifyFalse("Cloned grid should sorted in descending order",
            isAscending("gridThree_clone0"));
        verifyFalse("Serialized grid should sorted in descending order",
            isAscending("gridThree_serialize1"));

        checkNotSync("gridThree", "gridThree_clone0");
        checkNotSync("gridThree", "gridThree_serialize1");
        checkNotSync("gridThree_clone0", "gridThree_serialize1");

    }
   );
  }
}