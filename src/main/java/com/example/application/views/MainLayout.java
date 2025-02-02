package com.example.application.views;

import com.example.application.data.entity.User;
import com.example.application.security.AuthenticatedUser;
import com.example.application.views.Inicio.InicioView;
import com.example.application.views.estudiante.ListadeTareasEstudianteView;
import com.example.application.views.jefe_area.EstadisticasJAView;
import com.example.application.views.jefe_area.evaluacion.EvaluacionesView;
import com.example.application.views.jefe_area.tarea.TareaView;
import com.example.application.views.vicedecano.DashboardView;
import com.example.application.views.vicedecano.area.AreaView;
import com.example.application.views.vicedecano.estudiante.EstudiantesView;
import com.example.application.views.vicedecano.lista_evaluaciones.ListadeEvaluacionesVicedecanoView;
import com.example.application.views.vicedecano.lista_tareas.ListadeTareasVicedecanoView;
import com.example.application.views.vicedecano.profesor.ProfesorView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
@PageTitle("Main")
public class MainLayout extends AppLayout {

    public static class MenuItemInfo {

        private String text;
        private String iconClass;
        private Class<? extends Component> view;

        public MenuItemInfo(String text, String iconClass, Class<? extends Component> view) {
            this.text = text;
            this.iconClass = iconClass;
            this.view = view;
        }

        public String getText() {
            return text;
        }

        public String getIconClass() {
            return iconClass;
        }

        public Class<? extends Component> getView() {
            return view;
        }

    }

    private H1 viewTitle;

    private AuthenticatedUser authenticatedUser;
    private AccessAnnotationChecker accessChecker;

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        addToDrawer(createDrawerContent());
    }

    private Component createHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassName("text-secondary");
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames("m-0", "text-l");
        viewTitle.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

//        Button logout = new Button("Log out", e -> authenticatedUser.logout());
        HorizontalLayout layout = new HorizontalLayout();
        layout.addClassNames("flex", "items-center", "my-s", "px-m", "py-xs");
        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();

            Avatar avatar = new Avatar(user.getUsername(), user.getProfilePictureUrl());
            avatar.addClassNames("me-xs");

            ContextMenu userMenu = new ContextMenu(avatar);
            userMenu.setOpenOnClick(true);
            userMenu.addItem("Cerrar sesión", e -> {
                authenticatedUser.logout();
            });

            Span name = new Span(user.getName());
            name.addClassNames("font-medium", "text-s", "text-secondary");
            layout.add(avatar, name);

        } else {

            Anchor loginLink = new Anchor(/*"login", "Sign in"*/);
            loginLink.setHref("login");
            loginLink.add(new Span("Acceder"));
            layout.add(loginLink);
            layout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
            layout.addClassNames("bg-base", "border-b", "border-contrast-10", "box-border");
        }

        HorizontalLayout header = new HorizontalLayout(toggle, viewTitle, layout);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.expand(viewTitle);
        header.addClassNames("bg-base", "border-b", "border-contrast-10", "box-border", "flex", "py-0", "px-m");

        return header;
    }

    private Component createDrawerContent() {
        H2 appName = new H2("Menu");
        appName.addClassNames("flex", "items-center", "h-xl", "m-0", "px-m", "text-m");

        com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(appName,
                createNavigation());
        section.addClassNames("flex", "flex-col", "items-stretch", "max-h-full", "min-h-full");
        return section;
    }

    private Nav createNavigation() {
        Nav nav = new Nav();
        nav.addClassNames("border-b", "border-contrast-10", "flex-grow", "overflow-auto");
        nav.getElement().setAttribute("aria-labelledby", "views");

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames("list-none", "m-0", "p-0");
        nav.add(list);

        for (RouterLink link : createLinks()) {
            ListItem item = new ListItem(link);
            list.add(item);
        }
        return nav;
    }

    private List<RouterLink> createLinks() {
        MenuItemInfo[] menuItems;
        menuItems = new MenuItemInfo[]{ //

            new MenuItemInfo("Inicio", "la la-home", InicioView.class), //

            //Vicedecano
            new MenuItemInfo("Estadisticas", "la la-chart-bar", DashboardView.class), //
            new MenuItemInfo("Area", "la la-university", AreaView.class), //
            new MenuItemInfo("Jefe de Área", "la la-user", ProfesorView.class), //
            new MenuItemInfo("Estudiantes", "la la-user-graduate", EstudiantesView.class), //
            new MenuItemInfo("Lista de Evaluaciones", "la la-etsy", ListadeEvaluacionesVicedecanoView.class), //
            new MenuItemInfo("Lista de Tareas", "la la-tasks", ListadeTareasVicedecanoView.class), //

            //Jefe de Area
            new MenuItemInfo("Estadisticas", "la la-chart-bar", EstadisticasJAView.class), //
            new MenuItemInfo("Tarea", "la la-th-list", TareaView.class), //
            new MenuItemInfo("Evaluacion", "la la-etsy", EvaluacionesView.class), //

            //Estudiante
            new MenuItemInfo("Lista Tareas", "la la-tasks", ListadeTareasEstudianteView.class), //
        };

        List<RouterLink> links = new ArrayList<>();
        for (MenuItemInfo menuItemInfo : menuItems) {
            if (accessChecker.hasAccess(menuItemInfo.getView())) {
                links.add(createLink(menuItemInfo));
            }
        }
        return links;
    }

    private static RouterLink createLink(MenuItemInfo menuItemInfo) {
        RouterLink link = new RouterLink();
        link.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");
        link.setRoute(menuItemInfo.getView());

        Span icon = new Span();
        icon.addClassNames("me-s", "text-l");
        if (!menuItemInfo.getIconClass().isEmpty()) {
            icon.addClassNames(menuItemInfo.getIconClass());
        }

        Span text = new Span(menuItemInfo.getText());
        text.addClassNames("font-medium", "text-s");

        link.add(icon, text);
        return link;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

    
    //tengo q modificar antes de agregarlo ojooo
    private Component barraDeMenu() {

        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);

        MenuItem home = createIconItem(menuBar, VaadinIcon.HOME, "HOME", "inicio");
        MenuItem ajustes = createIconItem(menuBar, VaadinIcon.COG, "SETTINGS", "");
        MenuItem notification = createIconItem(menuBar, VaadinIcon.BELL, "NOTIFICATION", "");
        
       
        
        for (MenuItem item : new MenuItem[] { home, ajustes, notification }) {
//            if (accessChecker.hasAccess(item.get)) {
//                links.add(createLink(menuItemInfo));
//            }
        }
      
        return menuBar;
    }

    private MenuItem createIconItem(MenuBar menu, VaadinIcon iconName, String ariaLabel, String pag) {
        Icon icon = new Icon(iconName);
        Anchor link = new Anchor(pag, icon);
        MenuItem item = menu.addItem(link);
        item.getElement().setAttribute("aria-label", ariaLabel);
       
        return item;
    }

}
