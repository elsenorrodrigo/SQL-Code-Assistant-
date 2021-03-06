/*
 * Copyright (c) 2009,2010 Serhiy Kulyk
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     2. Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     3. The name of the author may not be used to endorse or promote
 *       products derived from this software without specific prior written
 *       permission from the author.
 *
 * SQL CODE ASSISTANT PLUG-IN FOR INTELLIJ IDEA IS PROVIDED BY SERHIY KULYK
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL SERHIY KULYK BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.deepsky.lang.plsql.psi.resolve.psibased;

import com.deepsky.lang.plsql.psi.resolve.ResolveContext777;
import com.deepsky.lang.plsql.psi.resolve.NameNotResolvedException;
import com.deepsky.lang.plsql.psi.resolve.VariantsProcessor777;
import com.deepsky.lang.plsql.psi.resolve.impl.TableColumnContext;
import com.deepsky.lang.plsql.psi.GenericTable;
import com.deepsky.lang.plsql.psi.PlSqlElement;
import com.deepsky.lang.plsql.psi.PlainTable;
import com.deepsky.lang.plsql.struct.TableDescriptorLegacy;
import com.deepsky.lang.plsql.struct.Type;
import com.deepsky.lang.plsql.NotSupportedException;
import com.intellij.psi.PsiElement;

import java.util.List;
import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

public class PsiTableContext implements ResolveContext777 {

    GenericTable tab;

    public PsiTableContext(GenericTable tab) {
        this.tab = tab;
    }

    public String[] getVariants(String prefix) {
        return new String[0];
    }

    public PsiElement getDeclaration() {
        return tab;
    }

    public ResolveContext777 resolve(PsiElement elem) throws NameNotResolvedException {
        String text = elem.getText();
        TableDescriptorLegacy tdesc = tab.describe();
        if (tdesc == null) {
            if (tab instanceof PlainTable) {
                String table_name = ((PlainTable) tab).getTableName();
                throw new NameNotResolvedException("Not found definition for table " + table_name);
            } else {
                throw new NameNotResolvedException("Definition not found");
            }
        }
        return new TableColumnContext(tab, tdesc, text);
    }

    public Type getType() {
        throw new NotSupportedException();
    }

    @NotNull
    public VariantsProcessor777 create(int narrow_type) throws NameNotResolvedException {
        TableDescriptorLegacy tdesc = tab.describe();
        if(tdesc == null){
            throw new NameNotResolvedException("");
        }
        return new TableColumnVariantProcessor(tdesc);
    }


    static class TableColumnVariantProcessor implements VariantsProcessor777 {

        TableDescriptorLegacy tdesc;

        public TableColumnVariantProcessor(@NotNull TableDescriptorLegacy tdesc) {
            this.tdesc = tdesc;
        }

        public String[] getVariants(String prefix) {
            List<String> out = new ArrayList<String>();
            for (String name : tdesc.getColumnNames()) {
                if (name.toUpperCase().startsWith(prefix.toUpperCase())) {
                    out.add(name);
                }
            }

            return out.toArray(new String[out.size()]);
        }
    }


}
