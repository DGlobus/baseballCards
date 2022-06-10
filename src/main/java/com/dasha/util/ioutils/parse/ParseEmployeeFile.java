package com.dasha.util.ioutils.parse;

import java.util.List;

public interface ParseEmployeeFile {

    List<EmployeeParsed> read(String path);
}
