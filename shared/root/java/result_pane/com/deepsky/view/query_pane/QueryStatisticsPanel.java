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

package com.deepsky.view.query_pane;

import org.jetbrains.annotations.NotNull;
import com.deepsky.database.exec.SQLUpdateStatistics;
import com.deepsky.database.DBException;
import com.deepsky.lang.plsql.NotSupportedException;
import com.intellij.util.ui.UIUtil;

import javax.swing.*;
import java.awt.*;

public class QueryStatisticsPanel extends JPanel implements QueryResultPanel {

    private JComponent central;
    StatusLinePanel statusLine;

    public QueryStatisticsPanel() {
        super(new BorderLayout());

        central = new JPanel(new BorderLayout());
        central.setBackground(UIUtil.getTreeTextBackground());
        add(central, BorderLayout.CENTER);

        statusLine = new StatusLinePanel();
        add(statusLine, BorderLayout.SOUTH);
    }


    public void init(@NotNull Object data) {
        SQLUpdateStatistics stats = (SQLUpdateStatistics) data;

        setVisible(false);
        

        if (central != null) {
            remove(central);
        }
        central = new JPanel(new BorderLayout());
        central.setBackground(UIUtil.getTreeTextBackground());
        add(central, BorderLayout.CENTER);

        statusLine.setResponseMessage(stats.resultMessage());
        statusLine.setTimeSpent(stats.timeSpent());

        setVisible(true);
    }


    public boolean isRefreshSupported() {
        return false;
    }

    public boolean isExportSupported() {
        return false;
    }

    public void refresh() throws DBException {
        throw new NotSupportedException("Refresh operation on DML query not supported");
    }

    public void close() {
    }
}
