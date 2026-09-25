# -*- coding: utf-8 -*-
import os
import sys
import shutil

base_dir = os.path.dirname(os.path.abspath(__file__))
workspace_dir = os.path.dirname(base_dir)
sys.path.append(base_dir)

from head_and_auth import get_head_and_auth
from portal_view import get_portal_view
from units_views import get_units_views
from revision_grammar_views import get_revision_grammar_views
from exam_and_footer import get_exam_and_footer

print("Assembling complete Bienvenu 2 single-file web platform...")

full_html = "".join([
    get_head_and_auth(),
    get_portal_view(),
    get_units_views(),
    get_revision_grammar_views(),
    get_exam_and_footer()
])

root_html = os.path.join(workspace_dir, "index.html")
assets_html = os.path.join(workspace_dir, "app/src/main/assets/index.html")

with open(root_html, 'w', encoding='utf-8') as f:
    f.write(full_html)

os.makedirs(os.path.dirname(assets_html), exist_ok=True)
shutil.copyfile(root_html, assets_html)

print(f"Assembly completed successfully! Total size: {len(full_html)} bytes written to {root_html} and {assets_html}")
