# -*- coding: utf-8 -*-
import json
import os
import shutil
from templates import get_html_shell_top, get_html_shell_bottom

print("Building complete 1:1 Web/PWA Platform...")

# Load all 5 datasets
data_dir = "app/exported_data"
with open(os.path.join(data_dir, "french_course_data.json"), encoding="utf-8") as f:
    french_course = json.load(f)

with open(os.path.join(data_dir, "revision_documents.json"), encoding="utf-8") as f:
    revision_docs = json.load(f)

with open(os.path.join(data_dir, "revision_grammaire.json"), encoding="utf-8") as f:
    revision_gram = json.load(f)

with open(os.path.join(data_dir, "booklet_lieux_exercices.json"), encoding="utf-8") as f:
    booklet_lieux = json.load(f)

with open(os.path.join(data_dir, "mid_year_exam.json"), encoding="utf-8") as f:
    mid_exam = json.load(f)

all_data = {
    "course": french_course,
    "revisionDocs": revision_docs,
    "revisionGram": revision_gram,
    "bookletLieux": booklet_lieux,
    "midExam": mid_exam
}

data_json_str = json.dumps(all_data, ensure_ascii=False)

# Read dynamic app script and all renderers
from app_script import get_app_script
from unit1_renderer import get_unit1_renderer_code
from unit2_renderer import get_unit2_renderer_code
from unit3_renderer import get_unit3_renderer_code
from revision_grammar_renderer import get_revision_grammar_renderer_code
from exam_renderer import get_exam_renderer_code

body_content = """
    <!-- 1. PORTAL SCREEN -->
    <div id="screen-portal" class="space-y-6"></div>

    <!-- 2. UNIT 1 SCREEN -->
    <div id="screen-unit1" class="hidden space-y-5"></div>

    <!-- 3. UNIT 2 SCREEN -->
    <div id="screen-unit2" class="hidden space-y-5"></div>

    <!-- 4. UNIT 3 SCREEN -->
    <div id="screen-unit3" class="hidden space-y-5"></div>

    <!-- 5. REVISION SCREEN -->
    <div id="screen-revision" class="hidden space-y-5"></div>

    <!-- 6. GRAMMAR SCREEN -->
    <div id="screen-grammar" class="hidden space-y-5"></div>

    <!-- 7. EXAM SCREEN -->
    <div id="screen-exam" class="hidden space-y-5"></div>
"""

full_html = "".join([
    get_html_shell_top(),
    body_content,
    get_html_shell_bottom(),
    "<script>\n",
    "window.COURSE_DATA = " + data_json_str + ";\n",
    get_unit1_renderer_code(),
    "\n",
    get_unit2_renderer_code(),
    "\n",
    get_unit3_renderer_code(),
    "\n",
    get_revision_grammar_renderer_code(),
    "\n",
    get_exam_renderer_code(),
    "\n",
    get_app_script(),
    "\n</script>\n</body>\n</html>"
])

with open("index.html", "w", encoding="utf-8") as f:
    f.write(full_html)

shutil.copyfile("index.html", "app/src/main/assets/index.html")

print(f"SUCCESS: Built index.html ({len(full_html)} bytes) and copied to app/src/main/assets/index.html")
