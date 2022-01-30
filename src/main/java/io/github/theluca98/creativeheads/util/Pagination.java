/*
 * CreativeHeads
 * Copyright (C) 2022 Luca <https://github.com/TheLuca98>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.theluca98.creativeheads.util;

import com.google.common.collect.Lists;

import java.util.List;

import static com.google.common.base.Preconditions.checkElementIndex;

public class Pagination<E> {

    private final List<List<E>> pages;

    public Pagination(List<E> list, int pageSize) {
        this.pages = Lists.partition(list, pageSize);
    }

    public int pageCount() {
        return pages.size();
    }

    public List<List<E>> getPageList() {
        return pages;
    }

    public List<E> getPage(int index) {
        checkElementIndex(index, pageCount(), "Invalid page index");
        return pages.get(index);
    }

    public boolean hasPage(int index) {
        return index >= 0 && index < pageCount();
    }

}
