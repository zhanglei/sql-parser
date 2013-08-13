/**
 * Copyright 2011-2013 Akiban Technologies, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* The original from which this derives bore the following: */

/*

   Derby - Class org.apache.derby.impl.sql.compile.BetweenOperatorNode

   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to you under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */

package com.foundationdb.sql.parser;

/**
 * A BetweenOperatorNode represents a BETWEEN clause. The between values are
 * represented as a 2 element list in order to take advantage of code reuse.
 *
 */

public class BetweenOperatorNode extends BinaryListOperatorNode
{
    /**
     * Initializer for a BetweenOperatorNode
     *
     * @param leftOperand The left operand of the node
     * @param betweenValues The between values in list form
     */

    public void init(Object leftOperand, Object betweenValues) {
        ValueNodeList betweenVals = (ValueNodeList)betweenValues;
        assert betweenVals.size() == 2;
        super.init(leftOperand, betweenValues, "BETWEEN", null);
    }

}
