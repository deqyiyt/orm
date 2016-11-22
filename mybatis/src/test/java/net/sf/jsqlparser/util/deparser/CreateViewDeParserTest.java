/*
 * Copyright (C) 2015 JSQLParser.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package net.sf.jsqlparser.util.deparser;

import static junit.framework.TestCase.assertEquals;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserDefaultVisitor;
import net.sf.jsqlparser.parser.CCJSqlParserTreeConstants;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.parser.SimpleNode;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.create.view.CreateView;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author tw
 */
public class CreateViewDeParserTest {

    public CreateViewDeParserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    
    @Test
    public void testCreateViewASTNode() throws JSQLParserException {
        String sql = "CREATE VIEW test AS SELECT a, b FROM mytable";
        final StringBuilder b = new StringBuilder(sql);
        SimpleNode node = (SimpleNode) CCJSqlParserUtil.parseAST(sql);
        node.dump("*");
        assertEquals(CCJSqlParserTreeConstants.JJTSTATEMENT, node.getId());
        
        node.jjtAccept(new CCJSqlParserDefaultVisitor() {
            int idxDelta = 0;
            @Override
            public Object visit(SimpleNode node, Object data) {
                if (CCJSqlParserTreeConstants.JJTCOLUMN == node.getId()) {
                    b.insert(node.jjtGetFirstToken().beginColumn - 1 + idxDelta, '"');
                    idxDelta++;
                    b.insert(node.jjtGetLastToken().endColumn + idxDelta, '"');
                    idxDelta++;
                }
                return super.visit(node, data); 
            }
        }, null);
        
        assertEquals("CREATE VIEW test AS SELECT \"a\", \"b\" FROM mytable", b.toString());
    }
}
