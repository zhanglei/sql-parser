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

   Derby - Class org.apache.derby.impl.sql.compile.BitConstantNode

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

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.types.TypeId;

import java.sql.Types;

public class BitConstantNode extends ConstantNode
{
    private int bitLength;

    /**
     * Initializer for a BitConstantNode.
     *
     * @param arg1 A Bit containing the value of the constant OR The TypeId for the type of the node
     *
     * @exception StandardException
     */

    public void init(Object arg1) throws StandardException {
        super.init(arg1,
                   Boolean.TRUE,
                   0);
    }

    public void init(Object arg1, Object arg2) throws StandardException {
        String a1 = (String)arg1;

        byte[] nv = fromHexString(a1, 0, a1.length()); 

        Integer bitLengthO = (Integer)arg2;
        bitLength = bitLengthO.intValue();

        init(TypeId.getBuiltInTypeId(Types.BINARY),
             Boolean.FALSE,
             bitLengthO);

        setValue(nv);
    }

    /**
     * Fill this node with a deep copy of the given node.
     */
    public void copyFrom(QueryTreeNode node) throws StandardException {
        super.copyFrom(node);

        BitConstantNode other = (BitConstantNode)node;
        this.bitLength = other.bitLength;
    }

    /**
       Convert a hexidecimal string generated by toHexString() back
       into a byte array.

       @param s String to convert
       @param offset starting character (zero based) to convert.
       @param length number of characters to convert.

       @return the converted byte array. Returns null if the length is
       not a multiple of 2.
    */
    public static byte[] fromHexString(String s, int offset, int length) {
        if ((length%2) != 0)
            return null;

        byte[] byteArray = new byte[length/2];

        int j = 0;
        int end = offset+length;
        for (int i = offset; i < end; i += 2) {
            int high_nibble = Character.digit(s.charAt(i), 16);
            int low_nibble = Character.digit(s.charAt(i+1), 16);

            if (high_nibble == -1 || low_nibble == -1) {
                // illegal format
                return null;
            }

            byteArray[j++] = (byte)(((high_nibble << 4) & 0xf0) | (low_nibble & 0x0f));
        }
        return byteArray;
    }

}
