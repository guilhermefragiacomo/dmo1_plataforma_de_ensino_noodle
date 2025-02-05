package br.ifsp.edu.dmo1.noodle.ui.listeners

import br.ifsp.edu.dmo1.noodle.data.model.Course

interface CourseItemListener {
    fun onCourseClick(course: Course)
}