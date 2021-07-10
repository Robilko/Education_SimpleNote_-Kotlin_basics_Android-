package com.robivan.simplenotekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import android.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NoteListFragment.Contract, EditNoteFragment.Contract {
    companion object {
        private const val NOTE_LIST_FRAGMENT = "NOTE_LIST_FRAGMENT"
        private const val EDIT_NOTES_FRAGMENT = "EDIT_NOTES_FRAGMENT"
    }

    private var isTwoPanel = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDrawer(initToolbar())
        isTwoPanel = findViewById<View>(R.id.second_fragment_container) != null
        showNoteList()
    }

    private fun initDrawer(toolbar: Toolbar) {
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener {
            val id: Int = it.itemId
            if (navigateFragment(id)) {
                drawer.closeDrawer(GravityCompat.START)
                return@setNavigationItemSelectedListener true
            }
            return@setNavigationItemSelectedListener false
        }
    }

    private fun initToolbar(): Toolbar {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        return toolbar
    }

    private fun navigateFragment(id: Int): Boolean {
        when(id) {
            R.id.action_favorite, //TODO реализовать фрагмент с настройками приложения
            R.id.action_deleted, //TODO реализовать фрагмент с удаленными заметками
            R.id.action_settings //TODO реализвать фрагмент с избранными заметками
                -> {
                Toast.makeText(this@MainActivity, resources.getString(R.string.do_not_realised_toast), Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val search: MenuItem = menu.findItem(R.id.search_menu)
        val searchText: SearchView = search.actionView as SearchView
        searchText.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MainActivity, resources.getString(R.string.do_not_realised_toast), Toast.LENGTH_SHORT).show()
                return true //TODO
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true //TODO
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.search_menu -> {
                Toast.makeText(this, resources.getString(R.string.do_not_realised_toast), Toast.LENGTH_SHORT).show()
                return true
            } //TODO реализовать поиск в заметках
            R.id.about_app_menu -> {
                Toast.makeText(this, resources.getString(R.string.about_app_toast), Toast.LENGTH_LONG).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showNoteList() {
        setTitle(NoteListFragment.title)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_fragment_container, NoteListFragment(), NOTE_LIST_FRAGMENT)
            .commit()
    }

    private fun showEditNote() {
        showEditNote(null)
    }

    private fun showEditNote(note: NoteEntity?) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (!isTwoPanel) {
            setTitle(EditNoteFragment.getTitle(note == null))
            transaction.addToBackStack(null)
        }
        transaction.add(if (isTwoPanel) R.id.second_fragment_container else R.id.main_fragment_container,
            EditNoteFragment.newInstance(note), EDIT_NOTES_FRAGMENT).commit()
    }

    override fun createNewNote() {
        showEditNote()
    }

    override fun editNote(noteEntity: NoteEntity?) {
        showEditNote(noteEntity)
    }

    override fun saveNote(note: NoteEntity?) {
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.popBackStack()

        val noteListFragment: NoteListFragment = fragmentManager.findFragmentByTag(NOTE_LIST_FRAGMENT) as NoteListFragment
        noteListFragment.addNote(note)
        setTitle(NoteListFragment.title)
        val editNoteFragment: EditNoteFragment = fragmentManager.findFragmentByTag(EDIT_NOTES_FRAGMENT) as EditNoteFragment
        supportFragmentManager.beginTransaction().remove(editNoteFragment).commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.main_fragment_container)
        if (fragment is NoteListFragment)
            setTitle(NoteListFragment.title)
    }
}