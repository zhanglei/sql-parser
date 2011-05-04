/**
 * Copyright (C) 2011 Akiban Technologies Inc.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses.
 */

package com.akiban.sql.compiler;

import com.akiban.sql.TestBase;

import com.akiban.sql.parser.StatementNode;
import com.akiban.sql.parser.SQLParser;
import com.akiban.sql.compiler.AISBinder;
import com.akiban.sql.compiler.BindingNodeFactory;
import com.akiban.sql.compiler.BooleanNormalizer;
import com.akiban.sql.compiler.BoundNodeToString;
import com.akiban.sql.compiler.Grouper;
import com.akiban.sql.compiler.SubqueryFlattener;
import com.akiban.sql.compiler.TypeComputer;
import com.akiban.sql.views.ViewDefinition;

import com.akiban.ais.ddl.SchemaDef;
import com.akiban.ais.ddl.SchemaDefToAis;
import com.akiban.ais.model.AkibanInformationSchema;

import org.junit.Before;
import org.junit.Ignore;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

@Ignore
public class ASTTransformTestBase extends TestBase
{
  protected ASTTransformTestBase(String caseName, String sql, String expected) {
    super(caseName, sql, expected);
  }

  public static final File RESOURCE_DIR = 
    new File("src/test/resources/"
             + ASTTransformTestBase.class.getPackage().getName().replace('.', '/'));

  // Base class has all possible transformers for convenience.
  protected SQLParser parser;
  protected BoundNodeToString unparser;
  protected AISBinder binder;
  protected TypeComputer typeComputer;
  protected BooleanNormalizer booleanNormalizer;
  protected SubqueryFlattener subqueryFlattener;
  protected Grouper grouper;

  @Before
  public void makeTransformers() throws Exception {
    parser = new SQLParser();
    parser.setNodeFactory(new BindingNodeFactory(parser.getNodeFactory()));
    unparser = new BoundNodeToString();
    typeComputer = new TypeComputer();
    booleanNormalizer = new BooleanNormalizer(parser);
    subqueryFlattener = new SubqueryFlattener(parser);
    grouper = new Grouper(parser);
  }

  protected void loadSchema(File schema) throws Exception {
    String sql = fileContents(schema);
    SchemaDef schemaDef = SchemaDef.parseSchema("use user; " + sql);
    SchemaDefToAis toAis = new SchemaDefToAis(schemaDef, false);
    AkibanInformationSchema ais = toAis.getAis();
    binder = new AISBinder(ais, "user");
  }

  protected void loadView(File view) throws Exception {
    String sql = fileContents(view);
    binder.addView(new ViewDefinition(sql, parser));
  }

  protected String getTree(StatementNode stmt) throws IOException {
    StringWriter str = new StringWriter();
    stmt.treePrint(str);
    return str.toString().trim();
  }

}
